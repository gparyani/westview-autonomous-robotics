# Pseudocode #
```
var timerStart
boolean wasHit
final var hitWaitTime

while (far from beacon) (Note #1)
{
	move forward
	if (robot hits something)
	{
		set wasHit = true
		start timer (Note #2)
		back up (Note #3)
		turn left (Notes #3,4)
	}
	if (wasHit and (amount of time since timerStart >= hitWaitTime))
		turn back towards beacon
}
```

## Footnotes ##
  1. how do we know which sensor to head for?
  1. set timerStart to current time
  1. for a certain time
  1. can also be right, but has to be same direction every time (at least if we are using a wall-hugging method)
# Human language #
  1. move forward until you hit something.
  1. when you do, start a timer, and keep doing `[`move back, turn, move forward`]` until the timer stops
  1. if you hit something when the timer is running, reset the timer.
  1. when the timer finishes, turn towards the beacon and move forward again (i.e. go to beginning)