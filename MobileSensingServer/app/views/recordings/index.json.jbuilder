json.array!(@recordings) do |recording|
  json.extract! recording, :id, :user_id, :device_id, :title
  json.url recording_url(recording, format: :json)
end
