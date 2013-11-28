json.array!(@linear_accelerations) do |linear_acceleration|
  json.extract! linear_acceleration, :recording_id, :time, :x, :y, :z
  json.url linear_acceleration_url(linear_acceleration, format: :json)
end
