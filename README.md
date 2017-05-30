# ALGA

ALGA (ALGorithm Animator) is a java applet that animates and tries to explain, in a visual way, an algorithm. In this
case, Mergesort. In orderd to run it you need to have a working java environment installed (1.8 is recommended)

### How does it work?

The mechanics are very simple. The user can load numeric values, of type Integer or Double, in three ways: 

1. by manually inserting them
2. by generating random numbers in a range between 0 and the number of values to generate minus one (for Integers) or in
   a range between 0 and the number of values to generate (for Doubles)
3. by writing a simple input file with the type of numbers (Integer or Double) followed by the values themselves
   separated by spaces

To load the input, the user must click File > Load Input...

Once the input has been successfully loaded, the user can adjust the animation settings, by clicking File > Settings

There are fundamentally two settings:

1. **The animation "speed"**

    The animation speed can be automatic calculated based on the number of values, by choosing on of the three available
    options:
    1. Slow
    2. Medium
    3. Fast

    Alternatively, the user can insert manually the desired delay for the comparison animation and for the movement
    animation.


2. **The animation mode**, which can be:

    1. Step-by-Step: the user is able to advance the animation one step at a time
    2. Motion: the animation runs by itself

### Notes on the input size

The input size can be virtually any, but the application tends to become slower to load the animation as the input size
increases. For optimal experience, and input size less than or equal to 250 is recommended, and a size of 1000 should be
considered as a reasonable upper limit

### Input file syntax

The input file must respect this syntax:

```
TYPE value1 value2 ... valuen
```

where TYPE can be Integer or Double

### About and Github page

In the `About` menu the user can find simple instructions to use the app and the github page for the project



