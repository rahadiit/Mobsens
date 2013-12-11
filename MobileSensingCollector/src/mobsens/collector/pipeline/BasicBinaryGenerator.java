package mobsens.collector.pipeline;

public class BasicBinaryGenerator<LeftItem, RightItem> implements BinaryGenerator<LeftItem, RightItem>
{
	protected Consumer<? super LeftItem> leftConsumer;

	protected Consumer<? super RightItem> rightConsumer;

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
