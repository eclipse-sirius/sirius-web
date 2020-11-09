/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import React, { useState } from 'react';

import { Checkbox } from 'core/checkbox/Checkbox';
import { Label } from 'core/label/Label';
import { Radio } from 'core/radio/Radio';
import { Select } from 'core/select/Select';
import { Textfield } from 'core/textfield/Textfield';
import { Textarea } from 'core/textarea/Textarea';

import styles from './FormStory.module.css';

export const FormStory = () => {
  return (
    <div className={styles.form}>
      <TextfieldSection />
      <TextareaSection />
      <CheckboxSection />
      <SelectSection />
      <RadioSection />
    </div>
  );
};

export const TextareaSection = () => {
  const [state, setState] = useState({
    value: '',
  });

  const onChange = (event) => {
    const { value } = event.target;
    setState((prevState) => {
      return { ...prevState, value };
    });
  };
  return (
    <div className={styles.textfieldSection}>
      <Label value="Description">
        <Textarea
          name="textarea"
          placeholder="Lorem ipsum&#x0a;Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
          value={state.value}
          rows={4}
          maxLength={10}
          onChange={onChange}
          data-testid="default"
        />
      </Label>
    </div>
  );
};

export const TextfieldSection = () => {
  const [state, setState] = useState({
    value: '',
  });

  const onChange = (event) => {
    const { value } = event.target;
    setState((prevState) => {
      return { ...prevState, value };
    });
  };
  return (
    <div className={styles.textfieldSection}>
      <Label value="Username">
        <Textfield
          type="text"
          name="textfield"
          placeholder="Type the user name"
          value={state.value}
          onChange={onChange}
          data-testid="default"
        />
      </Label>
    </div>
  );
};

export const CheckboxSection = () => {
  const [state, setState] = useState({
    default: true,
  });

  const onChange = (event) => {
    const { name, checked } = event.target;
    setState((prevState) => {
      return { ...prevState, [name]: checked };
    });
  };

  return (
    <div>
      <Checkbox name="active" checked={state.default} onChange={onChange} label="Is active" data-testid="active" />
    </div>
  );
};

const options = [
  { id: '1', label: 'Jakku' },
  { id: '2', label: 'Korriban' },
  { id: '3', label: 'Geonosis' },
  { id: '4', label: 'Coruscant' },
  { id: '5', label: 'Starkiller Base' },
];

export const SelectSection = () => {
  const [state, setState] = useState({
    planet: '3',
  });

  const onChange = (event) => {
    const { name, value } = event.target;
    setState((prevState) => {
      return { ...prevState, [name]: value };
    });
  };

  return (
    <div>
      <div className={styles.selectSection}>
        <Label value="Planet">
          <Select name="planet" value={state.planet} options={options} onChange={onChange} data-testid="planet" />
        </Label>

        <Label value="Planet (Small version)">
          <Select
            small={true}
            name="planet"
            value={state.planet}
            options={options}
            onChange={onChange}
            data-testid="small-planet"
          />
        </Label>
      </div>
    </div>
  );
};

export const RadioSection = () => {
  const options = [
    { id: '1', label: 'Jedi', selected: false },
    { id: '2', label: 'Sith', selected: true },
    { id: '3', label: 'Whills', selected: false },
  ];

  const [state, setState] = useState(options);

  const onChange = (event) => {
    const { value } = event.target;
    setState((prevState) => {
      return [...prevState].map((option) => {
        return { id: option.id, label: option.label, selected: option.id === value };
      });
    });
  };

  return (
    <div>
      <Label value="Alignment">
        <Radio name="alignment" options={state} onChange={onChange} data-testid="alignment" />
      </Label>
    </div>
  );
};
