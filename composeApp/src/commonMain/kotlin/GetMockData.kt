@file:Suppress("ALL")

import domain.use_case.spot.model.RejectionReason
import domain.use_case.spot.model.Spot
import domain.use_case.spot.model.SubmissionStatus
import domain.use_case.spot.model.SubmittedSpot
import domain.use_case.spot.model.VisitedSpot
import kotlin.random.Random

// TODO: do not use domain models in data layer
fun getSubmittedSpotsTestData() : List<SubmittedSpot>{
    val spots : List<Spot> = getSpotsTestData(null)
    return spots.map { spot: Spot ->
        SubmittedSpot(
            id = spot.id,
            title = spot.title,
            description = spot.description,
            submittedOn = Random.nextLong(1672531200000), // year 2023
            imageUrl = spot.imageUrl,
            status = getRandomState()
        )
    }
}

private fun getRandomState(): SubmissionStatus {

    return when (Random.nextInt(3)) { // Generates a random number between 0 and 2 (inclusive)
        0 -> SubmissionStatus.Submitted
        1 -> SubmissionStatus.Accepted
        2 -> SubmissionStatus.Rejected(RejectionReason.LOW_QUALITY)
        else -> throw IllegalStateException("Invalid random index")
    }
}

fun getVisitedSpotsTestData() : List<VisitedSpot>{
    val spots : List<Spot> = getSpotsTestData(null)
    return spots.map { spot: Spot ->
        VisitedSpot(
            id = spot.id,
            title = spot.title,
            description = spot.description,
            visitedOn = Random.nextLong(1672531200000), // year 2023
            imageByUserUrl = "https://qrstrees.com/wp-content/uploads/2017/10/cropped-shutterstock_268475741-1.jpg",
        )
    }
}

fun getSpotsTestData(currentUserCoordinatesInput: Pair<Double,Double>? = null) : List<Spot> {
    val currentUserCoordinates = currentUserCoordinatesInput ?: Pair(0.0, 0.0)
    return listOf(
        Spot(
            id = "SPOT01",
            title = "The Tiny Door",
            description = "Discover a mysterious tiny door hidden in plain sight, sparking curiosity and wonder.",
            coordinates = Pair(
               currentUserCoordinates.first + 0.003,
               currentUserCoordinates.second + 0.0005
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 45,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT02",
            title = "The Upside-Down House",
            description = "Explore this architectural oddity where everything is topsy-turvy, including your sense of balance.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.004,
                currentUserCoordinates.second + 0.003
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 35,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT03",
            title = "Singing Fence",
            description = "Listen to the melodic tunes created by the wind blowing through this unusual fence.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.001,
                currentUserCoordinates.second + 0.001
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 35,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT04",
            title = "The Bubblegum Wall",
            description = "Contribute to this colorful, ever-growing collage of chewed-up bubblegum.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.00136,
                currentUserCoordinates.second + 0.004532
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT05",
            title = "The Secret Garden",
            description = "Uncover a hidden urban oasis filled with lush greenery and vibrant street art.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.00213,
                currentUserCoordinates.second + 0.00455
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT06",
            title = "Whimsical Wonderland",
            description = "Step into a magical, interactive art installation that sparks joy and creativity.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.00613,
                currentUserCoordinates.second + 0.00845
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT07",
            title = "The Abandoned Carousel",
            description = "Discover the eerie beauty of a forgotten carousel hidden in the woods.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.00143,
                currentUserCoordinates.second + 0.00145
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT08",
            title = "The Shoe Tree",
            description = "Behold a tree adorned with an eclectic assortment of footwear hanging from its branches.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.00113,
                currentUserCoordinates.second + 0.00745
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT09",
            title = "The Staircase to Nowhere",
            description = "Climb this enigmatic outdoor staircase that seemingly leads to nowhere.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.00713,
                currentUserCoordinates.second + 0.00645
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT10",
            title = "The Hidden Hobbit Hole",
            description = "Find a charming, secluded dwelling straight out of a fantasy world.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.00513,
                currentUserCoordinates.second + 0.00245
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT11",
            title = "The Disappearing Door",
            description = "Can you spot the secret door that blends seamlessly with its surroundings?",
            coordinates = Pair(
                currentUserCoordinates.first + 0.00713,
                currentUserCoordinates.second + 0.00145
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT12",
            title = "The Paperclip Sculpture",
            description = "Admire an unusually large sculpture made entirely of paperclips.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.00413,
                currentUserCoordinates.second + 0.00945
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT13",
            title = "The Yarn-Bombed Bridge",
            description = "Cross a vibrant, colorful bridge adorned with cozy, knitted creations.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.00713,
                currentUserCoordinates.second + 0.00645
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT14",
            title = "The Chalkboard Tunnel",
            description = "Express your creativity on this tunnel's walls, transformed into a giant chalkboard canvas.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.00113,
                currentUserCoordinates.second + 0.00945
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT15",
            title = "The Inverted Traffic Cone",
            description = "Find a quirky, upside-down traffic cone that's become an unofficial landmark.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.007173,
                currentUserCoordinates.second + 0.00445
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT16",
            title = "The Sidewalk Diorama",
            description = "Peer into a miniature world built into the pavement, complete with tiny inhabitants.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.004135,
                currentUserCoordinates.second + 0.00452
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        ),
        Spot(
            id = "SPOT17",
            title = "The Teapot Water Tower",
            description = "Marvel at a quirky, teapot-shaped water tower that's been a local icon for decades.",
            coordinates = Pair(
                currentUserCoordinates.first + 0.002113,
                currentUserCoordinates.second + 0.03645
            ),
            imageUrl = "https://media.istockphoto.com/id/1290233518/photo/ginger-cat-portrait.jpg?b=1&s=170667a&w=0&k=20&c=D2ObzKSLDeuKyD5as2m_4UcdaPimE_uosgqWhVdt5n0=",
            likes = 94,
            dislikes = 65,
            isLiked = false,
            isDisliked = false,
            hashtags = listOf()
        )
    )
}