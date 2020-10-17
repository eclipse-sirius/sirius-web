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

export const Folder = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M4.8 5.8V2.2C4.8 1.88174 4.92643 1.57652 5.15147 1.35147C5.37652 1.12643 5.68174 1 6 1H13.6968L16.0968 3.4H22.8C23.1183 3.4 23.4235 3.52643 23.6485 3.75147C23.8736 3.97652 24 4.28174 24 4.6V16.6C24 16.9183 23.8736 17.2235 23.6485 17.4485C23.4235 17.6736 23.1183 17.8 22.8 17.8H19.2V21.4C19.2 21.7183 19.0736 22.0235 18.8485 22.2485C18.6235 22.4736 18.3183 22.6 18 22.6H1.2C0.88174 22.6 0.576515 22.4736 0.351472 22.2485C0.126428 22.0235 0 21.7183 0 21.4V7C0 6.68174 0.126428 6.37652 0.351472 6.15147C0.576515 5.92643 0.88174 5.8 1.2 5.8H4.8ZM4.8 8.2H2.4V20.2H16.8V17.8H4.8V8.2Z"></path>
    </svg>
  );
};
Folder.propTypes = propTypes;
