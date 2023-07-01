package blackjack.domain.gamer

import blackjack.domain.card.CardFixture
import blackjack.domain.card.cards
import blackjack.domain.state.Bust
import blackjack.domain.state.Hit
import blackjack.domain.state.Stay
import blackjack.domain.state.Wait
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class GamerTest : StringSpec({

    fun gamer(): Gamer {
        return object : Gamer() {
            override fun canHit(): Boolean {
                return true
            }
        }
    }

    "최초 플레이어의 상태는 wait 상태이다" {
        val gamer = gamer()
        (gamer.state is Wait) shouldBe true
    }

    "최초 카드를 전달하면 hit 상태이다" {
        val gamer = gamer()
        gamer.init(cards(CardFixture.heartAce, CardFixture.heartJack))
        (gamer.state is Hit) shouldBe true
    }

    "hit 이후에도 21점을 초과하지 않는다면 hit 상태이다" {
        val gamer = gamer()
        gamer.init(cards(CardFixture.heartTwo, CardFixture.heartThree)) // 2 + 3 = 5
        gamer.hit(CardFixture.heartFour) // 5 + 4 = 9
        gamer.hit(CardFixture.heartFive) // 9 + 5 = 14
        (gamer.state is Hit) shouldBe true
    }

    "최초 카드를 전달하지 않은 상태로 hit를 하면 RuntimeException 예외 처리를 한다" {
        val gamer = gamer()
        shouldThrow<RuntimeException> {
            gamer.hit(CardFixture.heartKing)
        }
    }

    "카드를 삽입 했다면 카드 목록이 비어있지 않다" {
        val gamer = gamer()
        gamer.init(cards(CardFixture.heartAce, CardFixture.heartJack))
        gamer.hasCard() shouldBe true
    }

    "카드를 삽입하지 않았다면 카드 목록이 비어있다" {
        val gamer = gamer()
        gamer.notHasCards() shouldBe true
    }

    "카드 삽입 후 21점을 초과하면 bust 상태가 된다" {
        val gamer = gamer()
        gamer.init(cards(CardFixture.heartQueen, CardFixture.heartJack)) // 20
        gamer.hit(CardFixture.heartKing)
        (gamer.state is Bust) shouldBe true
    }

    "stay를 하면 stay 상태가 된다" {
        val gamer = gamer()
        gamer.init(cards(CardFixture.heartQueen, CardFixture.heartJack)) // 20
        gamer.stay()
        (gamer.state is Stay) shouldBe true
    }

    "최초 카드를 전달하지 않은 상태로 stay를 하면 RuntimeException 예외 처리를 한다" {
        val gamer = gamer()
        shouldThrow<RuntimeException> {
            gamer.stay()
        }
    }
})