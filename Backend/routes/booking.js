import Booking from "../models/bookings.js";
import Car from "../models/cars.js"
import User from "../models/users.js";
import { Router } from "express";
import checkAccessToken from "../middleware/checkAccessToken.js";

const router = Router();

router.use(checkAccessToken)

router.post("booking", async (req, res) => {
  const { body } = req;
  const id=req.user._id
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
    const user=User.find({id})
    user.history.push(newBooking._id)
    res.status(200).send(newBooking);
  } catch (err) {
    console.log(err);
    res.status(401).send(err.message);
  }
});

router.delete("/booking/:id", async (req, res) => {
  const { id } = req.params;
  try {
    const deletedBooking = await Booking.findByIdAndDelete(id);
    if (!deletedBooking) {
      return res.status(404).send("Booking not found");
    }
    res
      .status(200)
      .send({ message: "Booking deleted successfully", deletedBooking });
  } catch (err) {
    console.log(err);
    res.status(500).send(err.message);
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

router.get("/bookings", async (req, res) => {
  try {
    const bookings = await Booking.find();
    if(!bookings||bookings.length===0){
      return res.status(404).send("can't find bookings")
    }
    res.status(200).send(bookings);
  } catch (err) {
    console.log(err);
    res.status(500).send(err.message);
  }
});

router.get("/total_bookings",async (req,res)=>{
  try{
    const bookings=await Booking.findAll()
    if(!bookings||bookings.length===0){
      res.status(404).send("No Bookngs found!")
    }
    else{
      res.status(200).send(bookings.length)
    }
  }
  catch(e){
    res.status(404).send("Some booking error")
  }
})

router.put("/booking/:id", async (req, res) => {
  const { id } = req.params;
  const { body } = req;
  try {
    const updatedBooking = await Booking.findByIdAndUpdate(id, body, {
      new: true, // Return the updated document
      runValidators: true, // Ensure the updated data adheres to the schema
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

export default router;
