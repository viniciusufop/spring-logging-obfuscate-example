package com.vfs.logging.objects

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ObfuscateTest {

    @Test
    fun `should analyse obfuscate method`() {
        Assertions.assertEquals("V**********s", "Vinicius".obfuscate())
        Assertions.assertEquals("B**********e", "Barte".obfuscate())
        Assertions.assertEquals("**", "ba".obfuscate())
        Assertions.assertEquals("*", "b".obfuscate())
        Assertions.assertEquals("*", "b".obfuscate())
    }

    @Test
    fun `should analyse obfuscate full method`() {
        Assertions.assertEquals("********", "Vinicius".obfuscateFull())
        Assertions.assertEquals("*****", "Barte".obfuscateFull())
        Assertions.assertEquals("**", "ba".obfuscateFull())
        Assertions.assertEquals("*", "b".obfuscateFull())
        Assertions.assertEquals("*", "b".obfuscateFull())
    }

    @Test
    fun `should analyse obfuscate credit card method`() {
        Assertions.assertEquals("535350******5232", "5353 5021 3579 5232".obfuscateCard())
        Assertions.assertEquals("535350******5232", "5353502135795232".obfuscateCard())
        Assertions.assertEquals("5**********3", "5353 5021 3579 523".obfuscateCard())
        Assertions.assertEquals("5**********3", "535350213579523".obfuscateCard())
        Assertions.assertEquals("5**********5", "5353 5021 3579 523345".obfuscateCard())
        Assertions.assertEquals("5**********5", "535350213579523345".obfuscateCard())
    }

    @Test
    fun `should analyse obfuscate document method`() {
        Assertions.assertEquals("***149190**", "83214919016".obfuscateDocument())
        Assertions.assertEquals("***014670**", "081.014.670-33".obfuscateDocument())
        Assertions.assertEquals("76.738.349/0001-97", "76.738.349/0001-97".obfuscateDocument())
        Assertions.assertEquals("31946740000149", "31946740000149".obfuscateDocument())
    }

    @Test
    fun `should analyse obfuscate name method`() {
        Assertions.assertEquals("", "".obfuscateName())
        Assertions.assertEquals("Vi******", "Vinicius".obfuscateName())
        Assertions.assertEquals("Ba*** Br****", "Barte Brasil".obfuscateName())
        Assertions.assertEquals("Vi****** Jo**", "Vinicius Jose".obfuscateName())
        Assertions.assertEquals("* as*", "b asd".obfuscateName())
        Assertions.assertEquals("* *", "b a".obfuscateName())
    }

    @Test
    fun `should analyse obfuscate phone method`() {
        Assertions.assertEquals("", "".obfuscatePhone())
        Assertions.assertEquals("***", "999".obfuscatePhone())
        Assertions.assertEquals("****", "9999".obfuscatePhone())
        Assertions.assertEquals("*********9999", "11 99999-9999".obfuscatePhone())
        Assertions.assertEquals("***********9999", "(11) 99999-9999".obfuscatePhone())
        Assertions.assertEquals("**********9999", "+5511999999999".obfuscatePhone())
    }

    @Test
    fun `should analyse obfuscate email method`() {
        Assertions.assertEquals("v**********s", "vinicius".obfuscateEmail())
        Assertions.assertEquals("vi******@ba***", "vinicius@barte".obfuscateEmail())
        Assertions.assertEquals("vi******@ba***.io", "vinicius@barte.io".obfuscateEmail())
        Assertions.assertEquals("vi******@ba***.com.br", "vinicius@barte.com.br".obfuscateEmail())
        Assertions.assertEquals("**@ba***.com.br", "vi@barte.com.br".obfuscateEmail())
    }
}
