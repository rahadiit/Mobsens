#!/bin/bash
rails generate scaffold Device identifier:string
rails generate scaffold Recording device:references
rails generate scaffold Accelerometer recording:references time:datetime x:float y:float z:float
rails generate scaffold Annotation recording:references time:datetime value:string
rails generate scaffold Battery recording:references time:datetime level:integer scale:integer
rails generate scaffold Gravity recording:references time:datetime x:float y:float z:float
rails generate scaffold Gyroscope recording:references time:datetime rX:float rY:float rZ:float
rails generate scaffold Light recording:references time:datetime ambientLight:float
rails generate scaffold LinearAcceleration recording:references time:datetime x:float y:float z:float
rails generate scaffold Location recording:references time:datetime longitude:float latitude:float speed:float bearing:float accuracy:float
rails generate scaffold MagneticField recording:references time:datetime x:float y:float z:float
rails generate scaffold Pressure recording:references time:datetime pressure:float
rails generate scaffold Proximity recording:references time:datetime proximity:float
rails generate scaffold RotationVector recording:references time:datetime xSinTheta:float ySinTheta:float zSinTheta:float
