import express from 'express';

const router = express.Router();

router.get('/read/:id', (req, res) => {
    res.send('You are reading post ' + req.params.id);
});

router.get('/about', (req,res) => {
    res.send('about');
});

module.exports = router;