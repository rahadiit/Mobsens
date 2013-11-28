json.array!(@gravities) do |gravity|
  json.extract! gravity, :recording_id, :time, :x, :y, :z
  json.url gravity_url(gravity, format: :json)
end
