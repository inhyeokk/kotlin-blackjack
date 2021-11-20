package blackjack.model

@JvmInline
value class Gamers private constructor(private val gamers: List<Gamer>) {

    fun players(): List<Player> = gamers.filterIsInstance<Player>()

    fun dealer(): Dealer? = gamers.filterIsInstance<Dealer>().firstOrNull()

    fun toList(): List<Gamer> = gamers

    fun receiveAll(
        count: Int = Int.MAX_VALUE,
        next: () -> Card?,
    ): Gamers {
        val limit = Array(gamers.size) { count }
        val hasNext: (Int) -> Boolean = { index -> limit[index]-- > 0 }
        return receiveWhile(
            next = { index, _ -> if (hasNext(index)) next() else null }
        )
    }

    fun receiveWhile(
        next: (Gamer) -> Card?,
        onReceive: (Gamer) -> Unit
    ): Gamers = receiveWhile(next = { _, gamer -> next(gamer) }, onReceive)

    private fun receiveWhile(
        next: (Int, Gamer) -> Card?,
        onReceive: (Gamer) -> Unit = { }
    ): Gamers = gamers.mapIndexed { index, gamer ->
        gamer.receiveWhile(
            next = { next(index, gamer) },
            onReceive = { cards -> onReceive(gamer.copy(cards = cards)) }
        )
    }.let(::Gamers)

    companion object {
        fun empty(): Gamers = Gamers(emptyList())

        fun from(dealer: Dealer, players: List<Player>) = Gamers(players + dealer)

        fun players(players: List<Player>) = Gamers(players)
    }
}