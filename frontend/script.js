document.getElementById('orderForm').addEventListener('submit', function(event){
    const radioButtons = document.getElementsByName('productId');
    let selectedId = null;

    for (let i = 0; i < radioButtons.length; i++) {
        if (radioButtons[i].checked) {
            selectedId = radioButtons[i].value;
            break;
        }
    }

    if (selectedId) {
        const qtyInput = document.querySelector('input[name="qty_' + selectedId + '"]');
        const row = qtyInput.closest('tr');
        const stockCell = row.querySelector('.stock-val');

        const qtyValue = parseInt(qtyInput.value);
        const stockValue = parseInt(stockCell.innerText);

        if (qtyValue < 1) {
            alert('Kuantitas tidak valid!');
            event.preventDefault();
        }

        if (qtyValue > stockValue) {
            alert('Stok tidak mencukupi!');
            event.preventDefault();
        }
    }
});