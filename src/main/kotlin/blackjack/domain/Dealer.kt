package blackjack.domain

object Dealer {
    private val cards = CardDeck.makeShuffledCard().toMutableList()

    fun popOneCard(): Card {
        return cards.removeLast()
    }
}