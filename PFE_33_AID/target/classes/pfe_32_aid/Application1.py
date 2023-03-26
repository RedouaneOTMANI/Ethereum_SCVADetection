import os
import sys
import pandas as pd
import numpy as np 
from textwrap import wrap
from keras.models import load_model
from keras.layers import Dense, Embedding, LSTM, SpatialDropout1D
from keras.models import Sequential
from sklearn.feature_extraction.text import CountVectorizer
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
from sklearn.model_selection import train_test_split
from keras.utils.np_utils import to_categorical

def read_file(file):
        
    df = pd.read_csv(file, header=None)
    df.columns=['OPCODE']
    df.to_csv(file+'.csv', index=None)
    df['CATEGORY'] = ""
    df.insert(0, 'ADDRESS', name)
    
    if len(df.iloc[0,1]) <= 4096:
        df['OUTPUT'] = df['OPCODE'].str[:2].apply(lambda x: 'true' if x == '0x' else 'false')

        if df.loc[0, 'OUTPUT'] == 'true':

            df1 = prepare(df)
            df2 = espace(df1)

            df2.to_csv(file+'.csv', index=None)

            return(df2)
        else:
            os.remove(path+"\\"+name+"\\Bytecode-OPCode\\"+r"bytecodeML.txt.csv")
            print('The format of your bytecode is incorrect, it must start with the two characters : 0x')
            sys.exit()
    else:
        os.remove(path+"\\"+name+"\\Bytecode-OPCode\\"+r"bytecodeML.txt.csv")
        print('The size of your bytecode exceeds 4096 bytes !')
        sys.exit()

def prepare(df):
    df['OPCODE'] = df['OPCODE'].str[2:]
    df.update(df)
    
    return(df)

def espace(df):
    df.iloc[0,1] = ' '.join(df.iloc[0,1][i:i+2] for i in range(0, len(df.iloc[0,1]), 2))
    df.update(df)
    return(df)

def label(df):    
    df['LABEL'] = 0
    df.loc[df['CATEGORY'] == '1 0 0 0', 'LABEL'] = 0
    df.loc[df['CATEGORY'] != '1 0 0 0', 'LABEL'] = 1
    
def preprocess(df):
    n_most_common_words = 1000 
    max_len = 130

    tokenizer = Tokenizer(num_words=n_most_common_words, lower=False)
    tokenizer.fit_on_texts(df['OPCODE'].values)
    sequences = tokenizer.texts_to_sequences(df['OPCODE'].values)
   
    word_index = tokenizer.word_index

    X = pad_sequences(sequences, maxlen=max_len)
    return X

def dftoXY(df):
    X_test = preprocess(df)
    
    label(df)
    
    y_test = to_categorical(df['LABEL'], num_classes=2)
    
    return X_test, y_test    

try:

    path = os.path.relpath('Smart Contracts')
    name = os.listdir(path)[0]
    
    file = read_file("Smart Contracts\\"+name+"\\Bytecode-OPCode\\"+r"bytecodeML.txt")

    model = load_model('model1-b-2.h5')
    X_test, y_pred = dftoXY(file)
    y_pred = model.predict_classes(X_test, batch_size=32, verbose=0)

    l = len(y_pred)
    for i in range(0,l):
        if y_pred[i] == 0:
            result = 'Not vulnerable to attack'
            file.iloc[i,2] = '1 0 0 0'
            file.iloc[i,3] = 0
        else:
            result = 'Vulnerable to attack'
            file.iloc[i,2] = '0 0 0 1'
            file.iloc[i,3] = 1
        print(result);
    
        file.to_csv(path+"\\"+name+"\\Bytecode-OPCode\\"+r"result.csv", index=False)
        os.remove(path+"\\"+name+"\\Bytecode-OPCode\\"+r"bytecodeML.txt.csv")
except Exception as e:
    print(e)
