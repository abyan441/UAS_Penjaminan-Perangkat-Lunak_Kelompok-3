<?php

require_once 'CheckoutLogic.php';

$logic = new CheckoutLogic();

$productId = $_POST['productId'] ?? '';
$customerName = $_POST['customerName'] ?? '';
$isMember = $_POST['isMember'] ?? 'false';
$promoCode = $_POST['promoCode'] ?? '';

$qty = $_POST["qty_$productId"] ?? 0;
$price = $_POST["price_$productId"] ?? 0;

$payload = $logic->buildPayload(
    $productId,
    $customerName,
    $isMember,
    $promoCode,
    $qty,
    $price
);

$ch = curl_init('http://localhost:8080/api/order');

curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_POST, true);
curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);
curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);

$response = curl_exec($ch);
$httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

curl_close($ch);

header(
    "Location: " .
    $logic->getRedirectUrl(
        $httpCode,
        $customerName,
        $response
    )
);

exit();