<?php

class CheckoutLogic
{
    public function buildPayload(
        $productId,
        $customerName,
        $isMember,
        $promoCode,
        $qty,
        $price
    ) {
        return json_encode([
            'productId' => $productId,
            'customerName' => $customerName,
            'isMember' => $isMember === 'true',
            'promoCode' => $promoCode,
            'qty' => (int)$qty,
            'price' => (float)$price
        ]);
    }

    public function buildMessage(
        $httpCode,
        $customerName,
        $response
    ) {
        if ($httpCode == 200) {
            return "Pesanan berhasil: " . $response;
        }

        return $customerName .
               ", pesanan gagal. " .
               $response;
    }

    public function getRedirectUrl(
        $httpCode,
        $customerName,
        $response
    ) {
        return "index.php?msg=" .
               urlencode(
                   $this->buildMessage(
                       $httpCode,
                       $customerName,
                       $response
                   )
               );
    }
}