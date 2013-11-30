json.array!(@recordings) do |recording|
  json.extract! recording, :user_id, :device_id, :title
  json.url recording_url(recording, format: :json)
end
