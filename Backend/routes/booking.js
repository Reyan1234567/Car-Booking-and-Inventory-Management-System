import Booking from "../models/bookings.js";
import Car from "../models/cars.js";
import User from "../models/users.js";
import { Router } from "express";
import checkAccessToken from "../middleware/checkAccessToken.js";

const router = Router();

// 
router.post("/booking", async (req, res) => {
  const { body } = req;
  const id = req.user._id;
  try {
    //   const convertToDate = (stringDate) => {
    //     const [date, month, year] = stringDate.split("/");
    //     const newDate = new Date(year, month - 1, date);
    //     return newDate;
    // };

    // const startDate=convertToDate(body.startDate)
    // const endDate=convertToDate(body.endDate)
    // const difference=endDate-startDate
    //   const ChosenCar=await Car.find({
    //     _id:id
    //   })
    //   const EstimatedCar=(ChosenCar.hourlyRate*difference)
    const response = new Booking(body);
    const newBooking = await response.save();
    const user = User.find({ id });
    user.history.push(newBooking._id);
    res.status(200).send(newBooking);
  } catch (err) {
    console.log(err);
    res.status(401).send(err.message);
  }
});

router.delete("/booking/:_id", async (req, res) => {
  const { _id } = req.params;
  try {
    const deletedBooking = await Booking.findByIdAndDelete(_id);
    if (!deletedBooking) {
      return res.status(404).send("Booking not found");
    }
    res
      .status(200)
      .send({ message: "Booking deleted successfully" });//deleted Booking
  } catch (err) {
    console.log(err);
    res.status(400).send(err.message);
  }
});

router.get("/booking/:id", async (req, res) => {
  const { id } = req.params;
  try {
    const booking = await Booking.findById(id);
    if (!booking) {
      return res.status(404).send("Booking not found");
    }
    res.status(200).send(booking);
  } catch (err) {
    console.log(err);
    res.status(500).send(err.message);
  }
});

// router.get("/bookings", async (req, res) => {
//   try {
//     const bookings = await Booking.find();
//     if (!bookings || bookings.length === 0) {
//       return res.status(404).send("can't find bookings");
//     }
//     res.status(200).send(bookings);
//   } catch (err) {
//     console.log(err);
//     res.status(500).send(err.message);
//   }
// });

router.get("/total_bookings", async (req, res) => {
  try {
    const bookings = await Booking.findAll();
    if (!bookings || bookings.length === 0) {
      res.status(404).send("No Bookngs found!");
    } else {
      res.status(200).send(bookings.length);
    }
  } catch (e) {
    res.status(404).send("Some booking error");
  }
});

router.patch("/booking/:id", async (req, res) => {
  const { id } = req.params;
  const { body } = req;
  try {
    const updates = {};
    for (const key in body) {
      if (!body.hasOwnProperty(key)) continue;

      if (!body[key]) {
        continue;
      } else {
        updates[key] = body[key];
      }
    }
    const updatedBooking = await Booking.findByIdAndUpdate(id, updates, {
      new: true,
      runValidators: true,
    });
    if (!updatedBooking) {
      return res.status(404).send("Booking not found");
    }
    res.status(200).send(updatedBooking);
  } catch (err) {
    console.log(err);
    res.status(500).send(err.message);
  }
});

router.get("/bookings", async (req, res) => {
  try {
    console.log("IN the BOOKING API")
    const final = Booking.aggregate([
      {
        $lookup: {
          from: "users",
          localField: "userId",
          foreignField: "_id",
          as: "userIds",
        },
      },
      { $unwind: "$userIds" },
      {
        $lookup: {
          from: "cars",
          localField: "carId",
          foreignField: "_id",
          as: "carIds",
        },
      },
      { $unwind: "$carIds" },
      {
        $lookup: {
          from: "profilephotos",
          localField: "userIds.profilePhoto",
          foreignField: "_id",
          as: "profilePhotos",
        },
      },
      { $unwind: "$profilePhotos" },
      {
        $lookup: {
          from: "carimages",
          localField: "carIds.image",
          foreignField: "_id",
          as: "carImages",
        },
      },
      {
        $project: {
          _id: 1,
          userIds: 0,
          carIds: 0,
        },
      },
    ]);
    if (!final) {
      console.log("couldn't find bookings")
      return res.status(404).send("Not found!");
    }
    res.sendStatus(200);
    console.log("bookings found and also images")
  } catch (e) {
    res.status(400).send(e.message);
  }
});

router.patch("/confirm",async(req,res)=>{
  const id=req.query._id
  const body={bookngStatus:"Confirmed"}
  const booking=await Booking.findByIdAndUpdate(id,body,{
    new:true,
    funValidation:true
  })
  if(!booking){
    return res.status(404).send("Booking Not found!")
  }
  res.status(200).send("Confirm updated sunccessfully")
})

router.patch("/cancel",async(req,res)=>{
  const id=req.query._id
  const body={bookngStatus:"Cancelled"}
  const booking=await Booking.findByIdAndUpdate(id,body,{
    new:true,
    funValidation:true
  })
  if(!booking){
    return res.status(404).send("Booking Not found!")
  }
  res.status(200).send("Cancel updated sunccessfully")
})

export default router;
