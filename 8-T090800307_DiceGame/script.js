document.getElementById('rollButton').addEventListener('click', function () {
    const resultElement = document.getElementById('result');
    const diceElements = [
        document.getElementById('dice1'),
        document.getElementById('dice2'),
        document.getElementById('dice3')
    ];

    // Roll the dice with animation
    diceElements.forEach(dice => {
        dice.classList.add('roll-animation');
    });

    setTimeout(() => {
        // Remove the animation class
        diceElements.forEach(dice => {
            dice.classList.remove('roll-animation');
        });

        // Generate random numbers for each player
        const diceRolls = [rollDice(), rollDice(), rollDice()];

        // Log the results to the console
        console.log(`Player 1 rolled: ${diceRolls[0]}`);
        console.log(`Player 2 rolled: ${diceRolls[1]}`);
        console.log(`Player 3 rolled: ${diceRolls[2]}`);

        // Update the dice display with dots
        diceElements.forEach((dice, index) => {
            updateDiceFace(dice, diceRolls[index]);
        });

        // Determine the winner
        const maxRoll = Math.max(...diceRolls);
        const winners = diceRolls
            .map((roll, index) => (roll === maxRoll ? `Player ${index + 1}` : null))
            .filter(player => player);

        // Display the result
        if (winners.length === 1) {
            resultElement.textContent = `${winners[0]} Wins!`;
            resultElement.style.color = "#399918";
        } else {
            resultElement.textContent = "It's a Draw!";
            resultElement.style.color = "#e67e22";
        }
    }, 500);
});

// Function to roll a dice (returns a number between 1 and 6)
function rollDice() {
    return Math.floor(Math.random() * 6) + 1;
}

// Function to update dice face with dots
function updateDiceFace(diceElement, number) {
    // Clear previous dots
    diceElement.innerHTML = '';
    diceElement.className = 'dice';

    // Set the dice face class based on the number
    diceElement.classList.add(`dice-face-${number}`);

    // Add dots based on the number
    for (let i = 0; i < number; i++) {
        const dot = document.createElement('div');
        dot.classList.add('dot');
        diceElement.appendChild(dot);
    }
}
