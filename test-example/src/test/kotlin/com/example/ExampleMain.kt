package com.example

import com.appmattus.kotlinfixture.config.range
import com.appmattus.kotlinfixture.decorator.nullability.AlwaysNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.assertThrows
import kotlin.reflect.full.isSubclassOf

class ExampleMain : FreeSpec({

    "interface" {
        val obj = fixture<ExInterface>()
        // 인터페이스 객체를 생성하기 위해 패키지 경로에서 참조 가능한 구현체를 찾는다
        // see: com.appmattus.kotlinfixture.resolver.AbstractClassResolver
        obj.a shouldBe  "fixed string"
        obj.b.value shouldBe "fixed string"
    }

    "concrete class" {
        val obj = fixture<ExInterfaceImpl>()
        obj.a shouldBe "fixed string"
    }

    "NoImpl" {
        withClue("인터페이스, 추상 클래스의 구현체를 찾지 못하면 UnsupportedOperationException 처리") {
            assertThrows<UnsupportedOperationException> { fixture<NoImpl>() }
            assertThrows<UnsupportedOperationException> { fixture<NoImpl2>() }
        }
    }

    "example class" {
        val obj = fixture<ExampleClass> {
            property<ExampleClass, String>("d") { "d" }
            property<ExampleClass, String>("e") { "e" }
            property(ExampleClass::num) { range(1..20) }
        }
        obj.a shouldBe "fixed string"
        obj.b::class.isSubclassOf(ExInterface::class) shouldBe true
        obj.c shouldHaveAtLeastSize 1
        obj.checkPrivatePropertyD("d")
        obj.checkPrivatePropertyE("e")
        obj.num shouldBeInRange 1..20
    }

    "nullabilityStrategy" - {

        "NeverNullStrategy" {
            val obj = fixture<ExampleClass> {
                nullabilityStrategy(NeverNullStrategy)
            }
            obj.nullable shouldNotBe null
        }

        "AlwaysNullStrategy" {
            val obj = fixture<ExampleClass> {
                nullabilityStrategy(AlwaysNullStrategy)
            }
            obj.nullable shouldBe null
        }
    }
}) {

    companion object {
        private val fixture = kotlinFixture {
            factory<String> { "fixed string" }
        }
    }
}
