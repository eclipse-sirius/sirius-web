/***********************************************************************************************
 * Copyright (c) 2019, 2020 Obeo. All Rights Reserved.
 * This software and the attached documentation are the exclusive ownership
 * of its authors and was conceded to the profit of Obeo SARL.
 * This software and the attached documentation are protected under the rights
 * of intellectual ownership, including the section "Titre II  Droits des auteurs (Articles L121-1 L123-12)"
 * By installing this software, you acknowledge being aware of this rights and
 * accept them, and as a consequence you must:
 * - be in possession of a valid license of use conceded by Obeo only.
 * - agree that you have read, understood, and will comply with the license terms and conditions.
 * - agree not to do anything that could conflict with intellectual ownership owned by Obeo or its beneficiaries
 * or the authors of this software
 *
 * Should you not agree with these terms, you must stop to use this software and give it back to its legitimate owner.
 ***********************************************************************************************/
import React, { useContext, useCallback, useState } from 'react';

const defaultState = {
  ctrlPressed: false,
};

const KeyBindingContext = React.createContext(defaultState);

const KeyBindingProvider = ({ children }) => {
  const [state, setState] = useState(defaultState);
  const keyUpHandler = useCallback((event) => {
    switch (event.key) {
      case 'Meta':
      case 'Control':
        setState((prevState) => {
          return { ...prevState, ctrlPressed: false };
        });
        break;
      default:
        break;
    }
  }, []);
  const keyDownHandler = useCallback((event) => {
    if (event.key === 'Meta' || event.key === 'Control') {
      switch (event.key) {
        case 'Meta':
        case 'Control':
          setState((prevState) => {
            return { ...prevState, ctrlPressed: true };
          });
          break;
        default:
          break;
      }
    }
  }, []);

  return (
    <KeyBindingContext.Provider value={state}>
      <div onKeyDown={(e) => keyDownHandler(e)} onKeyUp={(e) => keyUpHandler(e)}>
        {children}
      </div>
    </KeyBindingContext.Provider>
  );
};

export const withKeyBinding = (Child) => {
  return () => {
    return (
      <KeyBindingProvider>
        <Child />
      </KeyBindingProvider>
    );
  };
};

export const useKeyBinding = () => {
  return useContext(KeyBindingContext);
};
