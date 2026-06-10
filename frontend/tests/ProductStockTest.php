<?php

use PHPUnit\Framework\TestCase;

class ProductStockTest extends TestCase
{
    private $seedFile;
    private $testFile;

    protected function setUp(): void
    {
        $this->seedFile =
            __DIR__ . '/../../db/products_seed.json';

        $this->testFile =
            __DIR__ . '/products_test.json';

        copy(
            $this->seedFile,
            $this->testFile
        );
    }

    public function testReduceStock()
    {
        $products =
            json_decode(
                file_get_contents($this->testFile),
                true
            );

        $products[0]['stock']--;

        file_put_contents(
            $this->testFile,
            json_encode($products)
        );

        $result =
            json_decode(
                file_get_contents($this->testFile),
                true
            );

        $this->assertEquals(
            49,
            $result[0]['stock']
        );
    }

    protected function tearDown(): void
    {
        if (file_exists($this->testFile)) {
            unlink($this->testFile);
        }
    }
}