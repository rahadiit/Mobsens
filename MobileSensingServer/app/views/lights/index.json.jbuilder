json.array!(@lights) do |light|
  json.extract! light, :recording_id, :time, :ambientLight
  json.url light_url(light, format: :json)
end
