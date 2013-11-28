json.array!(@gyroscopes) do |gyroscope|
  json.extract! gyroscope, :recording_id, :time, :rX, :rY, :rZ
  json.url gyroscope_url(gyroscope, format: :json)
end
