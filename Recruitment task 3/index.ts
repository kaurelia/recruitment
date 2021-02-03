const draw: Function = (n: number = 10): Function => {
  const drawLine: Function = (
    maxValue: number,
    previousValue: number = 0
  ): Function | void => {
    previousValue++;
    let value: string = "";
    value += " ".repeat(maxValue - previousValue);
    value += "*".repeat(2 * previousValue - 1);
    console.log(value);
    if (previousValue < maxValue) {
      return drawLine(maxValue, previousValue);
    }
  };
  return drawLine(n);
};
draw(10);
