import React, {useEffect, useRef, useState} from 'react';
import {
  View,
  Text,
  StyleSheet,
  Dimensions,
  Image,
  FlatList,
  TextInput,
  Pressable,
  Alert,
} from 'react-native';

type Props = {
  onContinue?: (newPassword: string) => void;
  onSignUp?: () => void;
};

const {width} = Dimensions.get('window');

const carousel = [
  // TODO: replace with your real assets
  require('./assets/img_1.png'),
  require('./assets/img_2.png'),
  require('./assets/img_3.png'),
  require('./assets/img_4.png'),
];

export default function ResetPasswordScreen({onContinue, onSignUp}: Props) {
  const [password, setPassword] = useState('');
  const [index, setIndex] = useState(0);
  const listRef = useRef<FlatList>(null);

  useEffect(() => {
    const id = setInterval(() => {
      const next = (index + 1) % carousel.length;
      setIndex(next);
      listRef.current?.scrollToIndex({index: next, animated: true});
    }, 3000);
    return () => clearInterval(id);
  }, [index]);

  const handleContinue = () => {
    const trimmed = password.trim();
    if (!trimmed) {
      Alert.alert('Validation', 'Please enter a new password');
      return;
    }
    if (onContinue) {
      onContinue(trimmed);
    } else {
      Alert.alert('Success', 'Password reset successfully!');
    }
  };

  return (
    <View style={styles.root}>
      <View style={styles.header}>
        <FlatList
          ref={listRef}
          horizontal
          pagingEnabled
          showsHorizontalScrollIndicator={false}
          data={carousel}
          keyExtractor={(_, i) => String(i)}
          renderItem={({item}) => (
            <Image source={item} style={styles.carouselImage} resizeMode="cover" />
          )}
          onMomentumScrollEnd={ev => {
            const next = Math.round(ev.nativeEvent.contentOffset.x / width);
            setIndex(next);
          }}
        />
        <View style={styles.overlay} />
        <Image
          source={require('./assets/logo_h.png')}
          style={styles.logo}
          resizeMode="contain"
        />
      </View>

      <View style={styles.sheet}>
        <Text style={styles.title}>Forgot Password?</Text>
        <Text style={styles.subTitle}>Re-enter New Password</Text>

        <View style={styles.inputCard}>
          <Image
            source={require('./assets/ic_key.png')}
            style={styles.keyIcon}
            resizeMode="contain"
          />
          <TextInput
            value={password}
            onChangeText={setPassword}
            placeholder="Enter New Password"
            placeholderTextColor="#9E9E9E"
            secureTextEntry
            autoCapitalize="none"
            autoCorrect={false}
            style={styles.input}
            textAlignVertical="center"
          />
        </View>

        <Pressable style={styles.continueBtn} onPress={handleContinue}>
          <Text style={styles.continueTxt}>Continue</Text>
        </Pressable>

        <View style={{flex: 1}} />

        <View style={styles.bottomRow}>
          <Text style={styles.smallTxt}>Don't have an account?</Text>
          <Pressable onPress={onSignUp}>
            <Text style={styles.signUp}>Sign up here!</Text>
          </Pressable>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  root: {flex: 1, backgroundColor: '#000'},
  header: {height: 260, width: '100%'},
  carouselImage: {width, height: 260},
  overlay: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: '#66F5CACA',
  },
  logo: {
    position: 'absolute',
    width: 60,
    height: 60,
    left: 20,
    bottom: 60,
  },
  sheet: {
    flex: 1,
    marginTop: -42,
    backgroundColor: '#F7EAEA',
    borderTopLeftRadius: 26,
    borderTopRightRadius: 26,
    paddingHorizontal: 26,
    paddingTop: 26,
    paddingBottom: 22,
  },
  title: {fontSize: 34, fontWeight: '800', color: '#5B1717'},
  subTitle: {
    marginTop: 32,
    fontSize: 16,
    fontWeight: '700',
    color: '#5B1717',
  },
  inputCard: {
    marginTop: 12,
    height: INPUT_HEIGHT,
    borderRadius: 10,
    backgroundColor: '#fff',
    elevation: 6,
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 12,
  },
  keyIcon: {
    width: 18,
    height: 18,
    tintColor: '#6B6B6B',
  },
  input: {
    flex: 1,
    marginLeft: 10,
    color: '#333',
    height: INPUT_HEIGHT,
    fontSize: 14,
    lineHeight: 18,
    paddingVertical: 0,
    paddingTop: 0,
    paddingBottom: 0,
    includeFontPadding: false,
  },
  continueBtn: {
    marginTop: 24,
    height: 46,
    alignSelf: 'center',
    width: 160,
    borderRadius: 23,
    backgroundColor: '#5B1717',
    justifyContent: 'center',
    alignItems: 'center',
  },
  continueTxt: {color: '#fff', fontWeight: '700', fontSize: 14},
  bottomRow: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 6,
  },
  smallTxt: {fontSize: 11, color: '#5B1717'},
  signUp: {
    marginLeft: 6,
    fontSize: 11,
    fontWeight: '700',
    color: '#5B1717',
    textDecorationLine: 'underline',
  },
});
