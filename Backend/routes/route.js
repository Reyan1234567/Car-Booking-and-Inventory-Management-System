import {Router} from "express"
import accountRoutes from "./account.js"
import bookingRoutes from "./booking.js"
import carRoutes from "./car.js"
import locationRoutes from "./locations.js"

const router=Router()
router.use(accountRoutes)
router.use(bookingRoutes)
router.use(carRoutes)
router.use(locationRoutes)


export default router