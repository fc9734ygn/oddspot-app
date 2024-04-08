package data

// Auth
const val ENDPOINT_REGISTER = "v1/auth/register"
const val ENDPOINT_LOGIN = "v1/auth/login"
const val ENDPOINT_AUTHENTICATE = "v1/auth/authenticate"
const val ENDPOINT_SECRET_INFO = "v1/auth/secret-info"

// Profile:
const val ENDPOINT_CHANGE_USERNAME = "v1/profile/change-username"

// Spot:
const val ENDPOINT_SUBMIT_SPOT = "v1/spot/submit-spot"
const val ENDPOINT_SPOTS = "v1/spot/spots-feed"
const val ENDPOINT_REPORT_SPOT = "v1/spot/report"
const val ENDPOINT_SUBMITTED_SPOTS = "v1/spot/submitted-spots"

// Wishlist:
const val ENDPOINT_WISHLIST = "v1/wishlist/wishlist"
const val ENDPOINT_WISHLIST_ADD = "v1/wishlist/add"
const val ENDPOINT_WISHLIST_REMOVE = "v1/wishlist/remove"


// Other:
const val MULTIPART_DATA_KEY = "data"
const val MULTIPART_IMAGE_KEY = "image"
const val UPLOAD_IMAGE_FILE_NAME = "upload_image"