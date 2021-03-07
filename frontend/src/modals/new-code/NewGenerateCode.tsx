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
import { ActionButton, Buttons } from 'core/button/Button';
import { Form } from 'core/form/Form';
import { Label } from 'core/label/Label';
import { Select } from 'core/select/Select';
import { Modal } from 'modals/Modal';
import PropTypes from 'prop-types';
import React, { useState } from 'react';

//import { request } from 'http';
const axios = require('axios');


const propTypes = {
  projectId: PropTypes.string.isRequired,
  documentId: PropTypes.string.isRequired,
  onObjectCreated: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const NewCodeModal = ({ projectId, documentId, onObjectCreated, onClose }) => {
  const initialState = {
    selectedChildCreationDescriptionId: undefined,
    selectedChildCreationDescriptionIdFront: undefined,
    childCreationDescriptions: ["PHP", "Django"],
  };
  const [state, setState] = useState(initialState);
  const { selectedChildCreationDescriptionId } = state;
  const { selectedChildCreationDescriptionIdFront } = state;
  // Used to update the selected child creation description id
  var message="";
  const setSelectedChildCreationDescriptionId = (event) => {
    const selectedChildCreationDescriptionId = event.target.value;

    setState((prevState) => {
      const newState = { ...prevState };
      newState.selectedChildCreationDescriptionId = selectedChildCreationDescriptionId;
      return newState;
    });
  };
  
  const setSelectedChildCreationDescriptionIdFront = (event) => {
    const selectedChildCreationDescriptionIdFront = event.target.value;

    setState((prevState) => {
      const newState = { ...prevState };
      newState.selectedChildCreationDescriptionIdFront = selectedChildCreationDescriptionIdFront;
      return newState;
    });
  };



  // Create the new child
  const onCreateChild = (event) => {
    event.preventDefault();
    axios.get("http://test.vanthsec.com:8080/api/projects/"+projectId+"/documents/"+documentId+"/generate/"+selectedChildCreationDescriptionId+"/"+selectedChildCreationDescriptionIdFront)
    .then(response => {
        console.log(response.data);
        window.location.href=response.data;
        if(response.data.includes("Error"))message="Error in generating code. Check your model please";
        else message="Done! Your generated code was downloaded";
    })
    .catch(e => {
        // Podemos mostrar los errores en la consola
        console.log(e);
    })
  };




  /*const req = request(
    {
      host: 'localhost:8080',
      path: "/"+projectId+"/"+documentId+"/generate/"+selectedChildCreationDescriptionId+"/"+selectedChildCreationDescriptionId,
      method: 'GET',
    },
    response => {
      response.on('data', (url) => {
        console.log(url);
      });
      response.on('end', () => {
        
      });
    }
  );*/
  const options = [
    { id: "language", label: "Select a language backend" },
    { id: "php", label: "PHP" },
    { id: "django", label: "django" }
  ];

  const optionsHTML = [
    { id: "language", label: "Select a language frontend" },
    { id: "polymer", label: "Polymer" }
  ];
  
  return (
    <Modal title="Run code Generator" onClose={onClose}>
      <Form onSubmit={onCreateChild}>
      <Label value="Language Frontend type">
          <Select
            name="stereotype"
            value={selectedChildCreationDescriptionIdFront}
            options={optionsHTML}
            onChange={setSelectedChildCreationDescriptionIdFront}
            autoFocus
          />
        </Label>
        <Label value="Language Backend type">
          <Select
            name="stereotype"
            value={selectedChildCreationDescriptionId}
            options={options}
            onChange={setSelectedChildCreationDescriptionId}
            autoFocus
          />
        </Label>
        <Label value={message}>
        <Buttons>
          <ActionButton type="submit" label="Create" data-testid="create-object" />
        </Buttons>
        </Label>
      </Form>
    </Modal>
  );
};
NewCodeModal.propTypes = propTypes;