
		# 1038VisionDocumentation
		
		## How To Get PhotonVision
		--------------------------
		1) Go to https://github.com/PhotonVision/photonvision 
		2) Scroll down until you see the releases section and click on the text that says "+ (whatever amount) releases"
		3) See if there are any recent developer versions of the PhotonVision code (marked with pre-release) and if there aren't any then download the latest stable version of the PhotonVision code (marked with latest)
		4) Make sure the download is in a spot you will remember for later
		
		
		## How To Etch PhotonVision For Your CoProcessor (Get PhotonVision from your computer to the SD card needed for the CoProcessor)
		------------------------------------------------
		1) Check a Driver Station Laptop and make sure it has balena Etcher / Etcher
		2) Open balena Etcher / Etcher
		3) Make sure you have a micro-sd card and an SD card adapter 
		4) insert the micro-sd card into the SD card adapter & then plug the SD card adapter into the Driver Station Laptop
		5) Click "Select Image" in balena Etcher / Etcher
		5) Find your copy of the latest version of PhotonVision that you got from the "How To Get PhotonVision" Steps and select the folder **DO NOT UNZIP IT**
		6) Then go on to select Drive & select the Drive of the SD card
		7) finally hit flash & wait for the SD card to finish being flashed, once it is finished unplug the SD card & the SD card adapter
		8) insert the SD card into our Orange Pi 5 Pro
		
		
		## How To Calibrate Camera
		--------------------------
		1) Make sure you are connected to 1038/1039 Controls WiFi & the robot is on
		2) Open PhotonVision UI
		3) Navigate to PhotonVision's Calibration Section in the UI under the Camera tab.
		4) Select a calibration resolution and populate the pattern spacing, marker size, and board size.
		- If you're using the provided charuco board, set the pattern spacing to the size of the black, the marker size to the size of the charuco marker, and the board size to the size of the charuco board.
		5) Make sure the board overlay matches the board in the image to ensure a stronger calibration. Capture enough images to cover the Camera's FOV (minimum 12). Once you've got your images click "Finish calibration" then wait.
		- Make sure that the final Calibration has an FOV of the calibration that is within 10 degrees of the camera's specified FOV, the mean error should be less than 1 pixel.
		### Tips
		1) Ensure your targets are in different positions and angles, with as big of a difference as possible.
		2) Ensure your calibration target is as big as possible.
		3) Ensure your target has enough white border around it.
		4) Ensure your camera stays in one position during calibration.
		5) All images should be from varying distances and angles.
		6) Take at least one image that covers the entire image area.
		7) Have good lighting.
		8) Ensure that the target doesn't bend or fold in any way.
		9) Avoid having targets that are parallel to the lens of the camera.
		
		
		## How To Configure Settings For The Robot By Team Number/Purpose
		-------------------------------------------------
		# note - The 1038 network/team number is for competition robots ONLY & the 1039 network/team number is for testing purposes/non-competition robots
		1) Make sure you are connected to 1038/1039 Controls Wifi & the robot is on 
		2) Go to http://photonvision.local:5800
		3) Click on the settings tab
		4) Scroll down until you see a place that says "Team Number"
		5) Type 1 of 2 numbers, '1039' or '1038' look at the note above for context as to which team number to use
		6) Scroll down until you see a button to save changes and click save
		
		
		## How To Backup PhotonVision Settings
		--------------------------------------
		1) Make sure you are connected to 1038/1039 Controls Wifi
		2) Go to http://photonvision.local:5800
		3) Go to the settings tab of http://photonvision.local:5800
		4) Scroll down until you see "export settings"
		5) Click the text that says "export settings"
		6) Wait for the exported settings to finish downloading
		7) Store the settings in the GitHub 20XX robot project repository
		
		
		## How To Restore PhotonVision Settings
		--------------------------------------
		1) Make sure you are connected to 1038/1039 Controls Wifi
		2) Find where you stored the previous settings
		3) Download the previous settings & make sure it is a compressed zip file
		4) Wait for the previous settings to finish downloading
		5) Head back to http://photonvision.local:5800
		6) Go to the settings tab of http://photonvision.local:5800
		7) Scroll down until you see "import settings"
		8) Click the "import settings" button
		9) Find and select the previous settings you just downloaded and select them
