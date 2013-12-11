package mobsens.collector.pipeline;

public class BasicBinaryGenerator<LeftItem, RightItem> implements BinaryGenerator<LeftItem, RightItem>
{
	private Consumer<? super LeftItem> leftConsumer;

	private Consumer<? super RightItem> rightConsumer;

	protected void offerLeft(LeftItem item)
	{
		if(hasLeftConsumer())
		{
			leftConsumer.consume(item);
		}
	}
	protected void offerRight(RightItem item)
	{
		if(hasRightConsumer())
		{
			rightConsumer.consume(item);
		}
	}

	@Override
	public boolean hasLeftConsumer()
	{
		return leftConsumer != null;
	}

	@Override
	public Consumer<? super LeftItem> getLeftConsumer()
	{
		return leftConsumer;
	}

	@Override
	public void setLeftConsumer(Consumer<? super LeftItem> consumer)
	{
		this.leftConsumer = consumer;
	}

	@Override
	public boolean hasRightConsumer()
	{
		return rightConsumer != null;
	}

	@Override
	public Consumer<? super RightItem> getRightConsumer()
	{
		return rightConsumer;
	}

	@Override
	public void setRightConsumer(Consumer<? super RightItem> consumer)
	{
		this.rightConsumer = consumer;
	}
}
