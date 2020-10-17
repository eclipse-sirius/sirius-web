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

export const Logout = ({ title, ...props }) => {
  return (
    <svg
      viewBox="0 0 24 26"
      xmlns="http://www.w3.org/2000/svg"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M2.4 22.8368H21.6V16.8368H24V24.0368C24 24.3551 23.8736 24.6603 23.6485 24.8853C23.4235 25.1104 23.1183 25.2368 22.8 25.2368H1.2C0.88174 25.2368 0.576515 25.1104 0.351472 24.8853C0.126428 24.6603 0 24.3551 0 24.0368V16.8368H2.4V22.8368ZM17.0064 8.4368L12.2664 3.6968L13.9632 2L21.6 9.6368L13.9632 17.2736L12.2664 15.5768L17.0064 10.8368H3.6V8.4368H17.0064Z"></path>
    </svg>
  );
};
Logout.propTypes = propTypes;
