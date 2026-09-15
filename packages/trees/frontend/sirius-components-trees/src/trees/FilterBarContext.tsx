/*******************************************************************************
 * Copyright (c) 2026 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import React, { useEffect, useState } from 'react';
import { FilterBarContextProps, FilterBarContextStates, FilterBarContextValue } from './FilterBarContext.types';

export const FilterBarContext = React.createContext<FilterBarContextValue>({
  isOpen: false,
  filterBarText: null,
  filterBarTreeFiltering: false,
  setFilterBarText: () => {},
  setFilterBarTreeFiltering: () => {},
  toggleFilter: () => {},
  onClose: () => {},
});

export const FilterBarContextProvider = ({ containerRef, children }: FilterBarContextProps) => {
  const [state, setState] = useState<FilterBarContextStates>({
    isOpen: false,
    filterBarText: null,
    filterBarTreeFiltering: false,
  });

  const setFilterBarText = (filterBarText: string) =>
    setState((prevState) => ({
      ...prevState,
      filterBarText: filterBarText,
    }));

  const setFilterBarTreeFiltering = (enabled: boolean) =>
    setState((prevState) => ({
      ...prevState,
      filterBarTreeFiltering: enabled,
    }));

  const onClose = () =>
    setState((prevState) => {
      return { ...prevState, isOpen: false, filterBarText: '', filterBarTreeFiltering: false };
    });

  const toggleFilter = () =>
    setState((prevState) => {
      return { ...prevState, isOpen: !prevState.isOpen, filterBarText: '', filterBarTreeFiltering: false };
    });

  useEffect(() => {
    const downHandler = (event) => {
      if ((event.ctrlKey === true || event.metaKey === true) && event.key === 'f' && event.target.tagName !== 'INPUT') {
        event.preventDefault();
        setState((prevState) => {
          return { ...prevState, isOpen: false, filterBarText: '', filterBarTreeFiltering: false };
        });
      }
    };
    const element = containerRef?.current;
    if (element) {
      element.addEventListener('keydown', downHandler);

      return () => {
        element.removeEventListener('keydown', downHandler);
      };
    }
    return () => {};
  }, [containerRef]);

  return (
    <FilterBarContext.Provider
      value={{
        isOpen: state.isOpen,
        filterBarText: state.filterBarText,
        filterBarTreeFiltering: state.filterBarTreeFiltering,
        setFilterBarText,
        setFilterBarTreeFiltering,
        toggleFilter,
        onClose,
      }}>
      {children}
    </FilterBarContext.Provider>
  );
};
