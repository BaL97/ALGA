# ALGA

ALGA (ALGorithm Animator) is a java applet that animates and tries to explain, in a visual way, an algorithm. In this
case, Mergesort. In orderd to run it you need to have a working java environment installed (1.8 is recommended)

## How does it work?

The mechanics are very simple. The user can load numeric values, of type Integer or Double, in three ways: 

1. by manually inserting them
2. by generating random numbers in a range between 0 and the number of values to generate minus one (for Integers) or in
   a range between 0 and the number of values to generate (for Doubles)
3. by writing a simple input file with the type of numbers (Integer or Double) followed by the values themselves
   separated by spaces

To load the input, the user must click File > Load Input...

Once the input has been successfully loaded, the user can adjust the animation settings, by clicking File > Settings

There are fundamentally two settings:

1. the animation "speed"

    The animation speed can be automatic calculated based on the number of values, by choosing on of the three available
    options:
    1. Slow
    2. Medium
    3. Fast

    Alternatively, the user can insert manually the desired delay for the comparison animation and for the movement
    animation.


2. the animation mode, which can be:

    1. Step-by-Step: the user is able to advance the animation one step at a time
    2. Motion: the animation runs by itself



