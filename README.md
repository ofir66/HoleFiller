# Description
An image processing library for filling holes in images.
This library gets two arguments from the user: 
1. The image to fill hole in - which we will call "main image".
2. The mask, which is an image as well and we will call it "mask image", that contains a black area representing the area of the hole in the main image (see `Running Example`).
       
When running the library with the described arguments - it will return a new image with the hole filled accoridng to an internal algorithm.

The library supports grayscale images, and if the given images are not grayscale - they will be converted to grayscale.


# Running Instructions
1. Make sure that Maven is installed on your computer.
2. Clone this git project or just download and unzip it.
3. Put your desired image and desired mask in the folder `input`.
4. Open command line.
5. Enter the `HoleFiller` folder from the command line.
6. Run the following command for compiling the library: `mvn compile`
7. Run the following command for executing the library, replacing `<image>` and `<mask>` with desired main image and mask image names:
`mvn exec:java -Dexec.args="<image> <mask>"`
8. The result will be saved in the folder `output`.


# Running Example
You can see that inside `input` folder we have an image named `Lenna.png` which contains a hole.
We also have an image named `Mask.png` inside `input` folder that represents the hole in `Lenna.png`.

In the `output` folder, we have an image named `Lenna.png` which is the result of applying the following commands from the `HoleFiller` folder: </br>
`mvn compile` </br>
`mvn exec:java -Dexec.args="Lenna.png Mask.png"`

