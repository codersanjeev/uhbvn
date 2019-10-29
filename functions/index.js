" use strict ";

const functions = require("firebase-functions");

// Firebase Admin initialization
var admin = require("firebase-admin");
var serviceAccount = require("./service-account.json");
admin.initializeApp({
	credential: admin.credential.cert(serviceAccount),
	databaseURL: "https://uhbvn-a456d.firebaseio.com"
});

// Get Google Sheets instance
const {
	google
} = require("googleapis");
const sheets = google.sheets("v4");

// Create JWT
const jwtClient = new google.auth.JWT({
	email: serviceAccount.client_email,
	key: serviceAccount.private_key,
	scopes: ["https://www.googleapis.com/auth/spreadsheets"] // read and write sheets
});

// Get data from RTDB
exports.copyDataToSheet = functions.database.ref("/data").onUpdate(async change => {
	let data = change.after.val();

	var valueArray = [];
	var tempArray = [];

	Object.keys(data).forEach((key, index) => {
		// data[key] == {circle : Rohtak, dateTime : 26-09-1997}
		var temp = data[key];
		Object.values(temp).forEach((value, index) => {
			tempArray.push(value);
		})
		valueArray.push(tempArray);
		tempArray = [];
	})

	// Do authorization
	await jwtClient.authorize();

	var sz = valueArray.length + 1;

	// Create Google Sheets request
	let request = {
		auth: jwtClient,
		spreadsheetId: "1MzdfKwIJUkBgSwKRaKAKGrMHTObdVFLcp_FAD-zneSw",
		range: "Sheet1!A2:AO" + sz,
		valueInputOption: "RAW",
		requestBody: {
			values: valueArray
		}
	};

	// Update data to Google Sheets
	await sheets.spreadsheets.values.update(request, (err, result) => {
		if(err){
			console.log(err);
		}
		else{
			console.log("Success");
		}
	});
});