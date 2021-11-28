package blackjack.domain.card

object CardFactory {

    const val FULL_CARD_COUNT = 52

    val cards = createAllCards()

    private fun createAllCards(): List<Card> {
        return Symbol.values().flatMap { symbol ->
            Type.values().map { type ->
                Card(symbol, type)
            }
        }
    }
}