json.array!(@recordings) do |recording|
  json.extract! recording, :device_id
  json.url recording_url(recording, format: :json)
end
