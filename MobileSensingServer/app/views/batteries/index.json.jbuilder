json.array!(@batteries) do |battery|
  json.extract! battery, :recording_id, :time, :level, :scale
  json.url battery_url(battery, format: :json)
end
