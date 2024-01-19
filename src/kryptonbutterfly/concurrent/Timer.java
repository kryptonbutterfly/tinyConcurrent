package kryptonbutterfly.concurrent;

import java.util.function.BooleanSupplier;
import java.util.function.LongConsumer;

import kryptonbutterfly.monads.opt.Opt;

public class Timer
{
	private long	delta;
	private long	start;
	private long	until;
	
	public Timer(long delta)
	{
		this.delta = delta;
		resetTimer();
	}
	
	public void resetTimer()
	{
		this.start	= System.currentTimeMillis();
		this.until	= start + delta;
	}
	
	public boolean timeOut()
	{
		return 0 < timeLeft();
	}
	
	public long timeLeft()
	{
		return until - System.currentTimeMillis();
	}
	
	public void doWhile(Opt<BooleanSupplier> condition, LongConsumer action)
	{
		BooleanSupplier unpackedCondition = condition.get(() -> () -> true);
		while (unpackedCondition.getAsBoolean() && !timeOut())
		{
			action.accept(timeLeft());
		}
	}
}