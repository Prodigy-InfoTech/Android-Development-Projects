import { StatusBar } from 'expo-status-bar';
import { LinearGradient } from 'expo-linear-gradient';
import React, { useState } from 'react';
import { Image } from 'expo-image';
import {
  Text,
  View,
  SafeAreaView,
  TextInput,
  TouchableOpacity,
  ActivityIndicator,
} from 'react-native';
import axios from 'axios';

export default function App() {
  const [search, setSearch] = useState('Mumbai');
  const [data, setData] = useState();
  const [loading, setLoading] = useState(false);

  const options = {
    method: 'GET',
    url: 'https://weather-by-api-ninjas.p.rapidapi.com/v1/weather',
    params: { city: 'Seattle' },
    headers: {
      'X-RapidAPI-Key': '2ecbc8d2f5mshe4ce12fbc8fc9a1p1b4865jsnf5d56f66cf12',
      'X-RapidAPI-Host': 'weather-by-api-ninjas.p.rapidapi.com',
    },
  };

  const fetchWeather = async () => {
    setLoading(true);
    try {
      const response = await axios.request(options);
      console.log(response.data);
      setData(response.data);
      setLoading(false);
    } catch (error) {
      console.error(error);
      setLoading(false);
    }
  };
  function formatTimestampToTime(timestamp) {
    const date = new Date(timestamp);
    let hours = date.getHours();
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const seconds = date.getSeconds().toString().padStart(2, '0');
    const meridiem = hours >= 12 ? 'PM' : 'AM';

    // Convert to 12-hour format
    if (hours > 12) {
      hours -= 12;
    } else if (hours === 0) {
      hours = 12;
    }

    const formattedTime = `${hours}:${minutes}:${seconds}`;
    return formattedTime;
  }

  function degreesToDirection(degrees) {
    const directions = [
      'North',
      'North-East',
      'East',
      'South-East',
      'South',
      'South-West',
      'West',
      'North-West',
    ];
    const index = Math.round((degrees % 360) / 45);
    const normalizedIndex = (index + 8) % 8;
    const direction = directions[normalizedIndex];
    return direction;
  }

  const blurhash =
    '|rF?hV%2WCj[ayj[a|j[az_NaeWBj@ayfRayfQfQM{M|azj[azf6fQfQfQIpWXofj[ayj[j[fQayWCoeoeaya}j[ayfQa{oLj?j[WVj[ayayj[fQoff7azayj[ayj[j[ayofayayayj[fQj[ayayj[ayfjj[j[ayjuayj[';

  return (
    <SafeAreaView className='w-full h-full'>
      <StatusBar style='auto' />

      <View className='mt-4 flex items-center justify-center w-full h-screen relative'>
        <Image
          className='w-full h-40'
          source={`http://source.unsplash.com/1980x1080/?${search}+city`}
          contentFit='cover'
          placeholder={blurhash}
          transition={1000}
        />
        <TextInput
          placeholder='Enter City'
          onChangeText={setSearch}
          className='bg-slate-200 w-[60%] my-2 px-4 py-2 rounded-lg'
        />
        <TouchableOpacity
          onPress={fetchWeather}
          className='w-[60%] p-4 mx-2 rounded-lg bg-emerald-600 mb-4'
        >
          <Text className='text-center text-white'>
            {loading ? <ActivityIndicator color={'#fff'} /> : 'Search'}
          </Text>
        </TouchableOpacity>

        <View
          className='mt-4 flex items-center justify-center gap-2
         w-full'
        >
          <View className='flex-row w-[50%] justify-between bg-slate-200 py-3 px-3 rounded-lg'>
            <Text>Temp</Text>
            <Text>{data?.temp | 0}</Text>
          </View>
          <View className='flex-row w-[50%] justify-between bg-slate-200 py-3 px-3 rounded-lg'>
            <Text>Cloud</Text>
            <Text>{data?.cloud_pct | 0}</Text>
          </View>
          <View className='flex-row w-[50%] justify-between bg-slate-200 py-3 px-3 rounded-lg'>
            <Text>Feel Like </Text>
            <Text>{data?.feels_like | 0}</Text>
          </View>
          <View className='flex-row w-[50%] justify-between bg-slate-200 py-3 px-3 rounded-lg'>
            <Text>Humidity </Text>
            <Text>{data?.humidity | 0}</Text>
          </View>
          <View className='flex-row w-[50%] justify-between bg-slate-200 py-3 px-3 rounded-lg'>
            <Text>Max Temp </Text>
            <Text>{data?.max_temp | 0}</Text>
          </View>
          <View className='flex-row w-[50%] justify-between bg-slate-200 py-3 px-3 rounded-lg'>
            <Text>Min Temp </Text>
            <Text>{data?.min_temp | 0}</Text>
          </View>
          <View className='flex-row w-[50%] justify-between bg-slate-200 py-3 px-3 rounded-lg'>
            <Text>Wind</Text>
            <Text>{degreesToDirection(data?.wind_degrees)}</Text>
          </View>
          <View className='flex-row w-[50%] justify-between bg-slate-200 py-3 px-3 rounded-lg'>
            <Text>Wind Speed</Text>
            <Text>{data?.wind_speed}</Text>
          </View>
        </View>

        {/* cloud_pct": 0, "feels_like": 21, "humidity": 70, "max_temp": 26,
        "min_temp": 17, "sunrise": 1696688180, "sunset": 1696729065, "temp": 21,
        "wind_degrees": 341, "wind_speed": 0.89 */}
      </View>
    </SafeAreaView>
  );
}
