<?php
$productsJson = file_get_contents('../db/products.json');
$products = json_decode($productsJson, true);
?>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>CoffeShop Order System</title>
</head>
<body>
    <h1>Selamat Datang di CoffeShop</h1>

    <?php if(isset($_GET['msg'])): ?>
        <div id="message" style="color: red;">
            <?php echo $_GET['msg']; ?>
        </div>
    <?php endif; ?>

    <form id="orderForm" action="checkout.php" method="POST">
        <label for="customerName">Nama Pelanggan:</label>
        <input type="text" id="customerName" name="customerName" required><br><br>

        <label for="isMember">Status Member:</label>
        <select id="isMember" name="isMember">
            <option value="false">Bukan Member</option>
            <option value="true">Member</option>
        </select><br><br>

        <label for="promoCode">Kode Promo:</label>
        <input type="text" id="promoCode" name="promoCode"><br><br>

        <h3>Menu Kopi:</h3>
        <table border="1">
            <thead>
                <tr>
                    <th>Pilih</th>
                    <th>Nama</th>
                    <th>Harga</th>
                    <th>Stok Tersedia</th>
                    <th>Kuantitas</th>
                </tr>
            </thead>
            <tbody>
                <?php foreach($products as $prod): ?>
                <tr>
                    <td><input type="radio" name="productId" value="<?php echo $prod['id']; ?>" required></td>
                    <td><?php echo $prod['name']; ?></td>
                    <td>Rp <?php echo $prod['price']; ?></td>
                    <td class="stock-val"><?php echo $prod['stock']; ?></td>
                    <td>
                        <input type="number" name="qty_<?php echo $prod['id']; ?>" class="qty-input" value="0">
                        <input type="hidden" name="price_<?php echo $prod['id']; ?>" value="<?php echo $prod['price']; ?>">
                    </td>
                </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
        <br>
        <button type="submit" id="btnSubmit">Proses Pesanan</button>
    </form>

    <script src="script.js"></script>
</body>
</html>