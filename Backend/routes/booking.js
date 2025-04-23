import Booking from "../models/bookings.js";
import { Router } from "express";

const router = Router();

router.post("booking", async (req, res) => {
  const { body } = req;
  try {
    const response = new Booking(body);
    const newBooking = await response.save();

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
    res.status(200).send(bookings);
  } catch (err) {
    console.log(err);
    res.status(500).send(err.message);
  }
});

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
