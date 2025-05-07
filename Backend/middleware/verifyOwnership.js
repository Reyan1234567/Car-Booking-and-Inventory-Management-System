const verifyOwnership = async (req, res, next) => {
    if (req.user.userId !== req.params.id) {
        return res.status(403).send("Cannot update other users' accounts");
    }
    next();
};

export default verifyOwnership