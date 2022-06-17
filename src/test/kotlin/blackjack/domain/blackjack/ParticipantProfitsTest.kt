package blackjack.domain.blackjack

import blackjack.domain.card.cards
import blackjack.domain.common.Money
import blackjack.domain.player.Dealer
import blackjack.domain.player.Player
import blackjack.domain.player.PlayerStatus
import blackjack.domain.player.Players
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ParticipantProfitsTest : DescribeSpec({

    describe("profit") {
        it("딜러와 플레이어별로 수익 금액을 확인할 수 있다") {
            val yohan = Player(
                name = "yohan",
                cards = cards {
                    card { "다이아몬드" to "Q" }
                    card { "다이아몬드" to "A" }
                },
                playerStatus = PlayerStatus.STAY,
                batting = Money.of(1000)
            )
            val pang = Player(
                "pang",
                cards = cards {
                    card { "다이아몬드" to "Q" }
                    card { "다이아몬드" to 2 }
                },
                playerStatus = PlayerStatus.STAY,
                batting = Money.of(1000)
            )
            val dealer = Dealer(
                cards = cards {
                    card { "다이아몬드" to "Q" }
                    card { "다이아몬드" to 9 }
                }
            )

            val result = ParticipantProfits.of(Players(listOf(yohan, pang)), dealer)

            assertSoftly {
                val playersProfit = result.playersProfit()
                playersProfit[0] shouldBe ParticipantProfit(
                    yohan,
                    Money.of(1500)
                )
                playersProfit[1] shouldBe ParticipantProfit(
                    pang,
                    Money.of(-1000)
                )
                result.dealerProfit() shouldBe ParticipantProfit(
                    dealer,
                    Money.of(-500)
                )
            }
        }
    }
})