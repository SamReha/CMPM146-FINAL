var fs = require('fs');

// Read and eval library
filedata = fs.readFileSync('./tracery/js/tracery.min.js','utf8');
eval(filedata);

/* The tracery.min.js file defines a class 'tracery' which is all we want to export */

exports.tracery = tracery;