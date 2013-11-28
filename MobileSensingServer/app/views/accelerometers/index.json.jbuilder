json.array!(@accelerometers) do |accelerometer|
  json.extract! accelerometer, :recording_id, :time, :x, :y, :z
  json.url accelerometer_url(accelerometer, format: :json)
end
