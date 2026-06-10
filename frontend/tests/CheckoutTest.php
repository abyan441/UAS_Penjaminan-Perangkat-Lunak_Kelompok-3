<?php

use PHPUnit\Framework\TestCase;

require_once __DIR__ . '/../CheckoutLogic.php';

class CheckoutTest extends TestCase
{
    public function testBuildPayload()
    {
        $logic = new CheckoutLogic();

        $payload = $logic->buildPayload(
            "P01",
            "Abyan",
            "true",
            "JAVACOFFEE",
            10,
            20000
        );

        $this->assertStringContainsString(
            '"productId":"P01"',
            $payload
        );

        $this->assertStringContainsString(
            '"customerName":"Abyan"',
            $payload
        );
    }

    public function testSuccessMessage()
    {
        $logic = new CheckoutLogic();

        $result = $logic->buildMessage(
            200,
            "Abyan",
            "Order Success"
        );

        $this->assertEquals(
            "Pesanan berhasil: Order Success",
            $result
        );
    }

    public function testFailedMessage()
    {
        $logic = new CheckoutLogic();

        $result = $logic->buildMessage(
            500,
            "Abyan",
            "Server Error"
        );

        $this->assertEquals(
            "Abyan, pesanan gagal. Server Error",
            $result
        );
    }

    public function testRedirectUrlSuccess()
    {
        $logic = new CheckoutLogic();

        $url = $logic->getRedirectUrl(
            200,
            "Abyan",
            "Order Success"
        );

        $this->assertStringContainsString(
            "index.php?msg=",
            $url
        );
    }

    public function testRedirectUrlFailed()
    {
        $logic = new CheckoutLogic();

        $url = $logic->getRedirectUrl(
            500,
            "Abyan",
            "Error"
        );

        $this->assertStringContainsString(
            "index.php?msg=",
            $url
        );
    }
}