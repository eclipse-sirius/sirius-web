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

export const LinkIcon = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M13.1875 7.64627L14.7712 9.22995C15.4987 9.95746 16.0759 10.8212 16.4697 11.7717C16.8634 12.7223 17.0661 13.7411 17.0661 14.77C17.0661 15.799 16.8634 16.8178 16.4697 17.7684C16.0759 18.7189 15.4987 19.5826 14.7712 20.3101L14.375 20.7052C12.9056 22.1745 10.9128 23 8.83487 23C6.75694 23 4.7641 22.1745 3.29478 20.7052C1.82546 19.2359 1 17.2431 1 15.1651C1 13.0872 1.82546 11.0944 3.29478 9.62504L4.87846 11.2087C4.35509 11.7275 3.93934 12.3446 3.65509 13.0245C3.37083 13.7044 3.22368 14.4338 3.22206 15.1708C3.22045 15.9077 3.36441 16.6377 3.64568 17.3189C3.92695 18 4.34 18.6189 4.8611 19.14C5.3822 19.6611 6.00109 20.0742 6.68225 20.3554C7.36341 20.6367 8.09341 20.7807 8.83036 20.7791C9.5673 20.7774 10.2967 20.6303 10.9766 20.346C11.6565 20.0618 12.2736 19.646 12.7924 19.1227L13.1886 18.7265C14.2377 17.677 14.827 16.2539 14.827 14.77C14.827 13.2862 14.2377 11.863 13.1886 10.8136L11.6049 9.22995L13.1886 7.64739L13.1875 7.64627ZM20.7063 14.3738L19.1238 12.7913C19.6472 12.2725 20.0629 11.6554 20.3471 10.9755C20.6314 10.2955 20.7786 9.56618 20.7802 8.82924C20.7818 8.09229 20.6378 7.36229 20.3566 6.68113C20.0753 5.99997 19.6622 5.38108 19.1411 4.85998C18.62 4.33888 18.0011 3.92584 17.32 3.64456C16.6388 3.36329 15.9088 3.21933 15.1719 3.22094C14.4349 3.22256 13.7056 3.36972 13.0256 3.65397C12.3457 3.93822 11.7286 4.35397 11.2098 4.87734L10.8136 5.27354C9.76454 6.32296 9.17519 7.74608 9.17519 9.22995C9.17519 10.7138 9.76454 12.1369 10.8136 13.1864L12.3973 14.77L10.8136 16.3526L9.23107 14.77C8.50349 14.0425 7.92634 13.1788 7.53257 12.2283C7.1388 11.2777 6.93613 10.2589 6.93613 9.22995C6.93613 8.20105 7.1388 7.18222 7.53257 6.23165C7.92634 5.28107 8.50349 4.41737 9.23107 3.68986L9.62727 3.29478C11.0966 1.82546 13.0894 1 15.1674 1C17.2453 1 19.2381 1.82546 20.7075 3.29478C22.1768 4.7641 23.0022 6.75693 23.0022 8.83487C23.0022 10.9128 22.1768 12.9056 20.7075 14.375L20.7063 14.3738Z"></path>
    </svg>
  );
};
LinkIcon.propTypes = propTypes;
