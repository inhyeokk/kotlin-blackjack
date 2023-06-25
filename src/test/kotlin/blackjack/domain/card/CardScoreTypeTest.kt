package blackjack.domain.card

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CardScoreTypeTest : StringSpec({

    "1은 점수의 범위인 2~10 사이가 아니므로 RuntimeException 예외 처리를 한다" {
        shouldThrow<RuntimeException> { CardScoreType.fixed(1) }
    }

    "11은 점수의 밤위인 2~10 사이가 아니므로 RuntimeException 예외 처리를 한다" {
        shouldThrow<RuntimeException> { CardScoreType.fixed(11) }
    }

    "2~10 사이의 숫자는 점수로 변환할 수 있다" {
        shouldNotThrow<Throwable> {
            (2..10).forEach {
                CardScoreType.fixed(it)
            }
        }
    }

    "flexible 타입의 작은 값은 1이다" {
        CardScoreType.Flexible.smallValue shouldBe 1
    }

    "flexible 타입의 큰 값은 11이다" {
        CardScoreType.Flexible.largeValue shouldBe 11
    }
})