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

export const Profile = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M12 24C5.3724 24 0 18.6276 0 12C0 5.3724 5.3724 0 12 0C18.6276 0 24 5.3724 24 12C24 18.6276 18.6276 24 12 24ZM4.3392 17.784C5.34774 16.7137 6.56481 15.8613 7.91537 15.2793C9.26593 14.6973 10.7214 14.398 12.192 14.4C13.61 14.3982 15.0144 14.6764 16.3245 15.2189C17.6346 15.7613 18.8247 16.5572 19.8264 17.5608C20.8537 16.115 21.4604 14.4128 21.5791 12.6431C21.6978 10.8734 21.3239 9.10546 20.499 7.53535C19.674 5.96523 18.4302 4.65435 16.9056 3.74813C15.3809 2.84192 13.635 2.37581 11.8615 2.4015C10.0881 2.4272 8.35639 2.9437 6.85862 3.89371C5.36086 4.84371 4.15555 6.19008 3.37643 7.78344C2.59731 9.3768 2.27483 11.1549 2.44478 12.9203C2.61472 14.6858 3.27045 16.3697 4.3392 17.7852V17.784ZM12 13.2C10.727 13.2 9.50606 12.6943 8.60589 11.7941C7.70571 10.8939 7.2 9.67304 7.2 8.4C7.2 7.12696 7.70571 5.90606 8.60589 5.00589C9.50606 4.10571 10.727 3.6 12 3.6C13.273 3.6 14.4939 4.10571 15.3941 5.00589C16.2943 5.90606 16.8 7.12696 16.8 8.4C16.8 9.67304 16.2943 10.8939 15.3941 11.7941C14.4939 12.6943 13.273 13.2 12 13.2Z"></path>
    </svg>
  );
};

Profile.propTypes = propTypes;
