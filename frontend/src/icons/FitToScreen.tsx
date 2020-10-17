/*******************************************************************************
 * Copyright (c) 2020 Remix Design Studio, Obeo and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Contributors:
 *     Remix Design Studio - initial implementation as SVG
 *     Obeo - conversion into JSX
 *******************************************************************************/
import React from 'react';
import PropTypes from 'prop-types';

const propTypes = {
  title: PropTypes.string.isRequired,
};

export const FitToScreen = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M22.8 0C23.1183 0 23.4235 0.140476 23.6485 0.390524C23.8736 0.640573 24 0.979711 24 1.33333V22.6667C24 23.0203 23.8736 23.3594 23.6485 23.6095C23.4235 23.8595 23.1183 24 22.8 24H1.2C0.88174 24 0.576515 23.8595 0.351472 23.6095C0.126428 23.3594 0 23.0203 0 22.6667V1.33333C0 0.979711 0.126428 0.640573 0.351472 0.390524C0.576515 0.140476 0.88174 0 1.2 0H22.8ZM21.6 2.66667H2.4V21.3333H21.6V2.66667ZM13.2 18.6667V16H16.8V12H19.2V18.6667H13.2ZM10.8 5.33333V8H7.2V12H4.8V5.33333H10.8Z"></path>
    </svg>
  );
};
FitToScreen.propTypes = propTypes;
