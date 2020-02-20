const express = require('express');
const router = require('./src/server/routes/posts');

const app = express();
const port = process.env.PORT || 3000;

app.use(express.static('dist'));

app.use('/', router);

app.get('/hello', (req, res) => {
  return res.send('Hello');
});

app.listen(port, () => console.log(`> Ready on http://localhost:${port}`));
